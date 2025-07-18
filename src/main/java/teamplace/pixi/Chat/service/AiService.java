package teamplace.pixi.Chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import teamplace.pixi.Chat.domain.Chat;
import teamplace.pixi.Chat.dto.AiChatRequest;
import teamplace.pixi.Chat.dto.AiChatResponse;
import teamplace.pixi.Chat.dto.ChatMessageDto;
import teamplace.pixi.Chat.repository.ChatRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiService {

    private static final String FASTAPI_URL = "http://43.202.59.38:8000/ai/chat";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ChatRepository chatRepository;

    public AiChatResponse sendToFastApi(AiChatRequest dto) {
        try {

            String sessionId = dto.getSessionId();

            if (dto.isNewChat() || sessionId == null || sessionId.isEmpty()){
                sessionId = UUID.randomUUID().toString();
            }
            // 0. 유저 메세지 저장
            Chat userMessage = Chat.builder()
                    .loginId(dto.getLoginId())
                    .sessionId(sessionId)
                    .isUser(true)
                    .content(dto.getMessage())
                    .timestamp(LocalDateTime.now())
                    .newChat(dto.isNewChat())
                    .build();
            chatRepository.save(userMessage);

            // 1. FastAPI 서버 연결 및 전송
            URL url = new URL(FASTAPI_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            // 2. JSON 문자열 작성
            dto.setSessionId(sessionId);
            String jsonInputString = objectMapper.writeValueAsString(dto);

            // 3. 요청 본문에 JSON 데이터 쓰기
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if(responseCode !=200){
                return new AiChatResponse("FastAPI 서버 오류" + responseCode, false, null);
            }
            // 4. 응답 읽기
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            // 4. JSON 응답 파싱
            AiChatResponse aiResponse = objectMapper.readValue(response.toString(), AiChatResponse.class);

            // 5. 챗봇 메시지 저장
            Chat aiMessage = Chat.builder()
                    .loginId(dto.getLoginId())
                    .sessionId(sessionId) //세션 아이디도 저장
                    .isUser(false)
                    .content(aiResponse.getReply())
                    .timestamp(LocalDateTime.now())
                    .build();
            chatRepository.save(aiMessage);

            aiResponse.setSessionId(sessionId);
            return aiResponse;


        } catch (Exception e) {
            log.error("FastAPI 연결 실패: {}", e.getMessage(),e);
            return new AiChatResponse("AI 서버 연결 실패", false, null);
        }
    }

    //대화 이력 조회 메서드
    public List<ChatMessageDto> getChatHistoryBySession(String loginId, String sessionId) {
        return chatRepository.findByLoginIdAndSessionIdOrderByTimestampAsc(loginId,sessionId)
                .stream()
                .map(chat -> ChatMessageDto.builder()
                        .content(chat.getContent())
                        .isUser(chat.getIsUser())
                        .timestamp(chat.getTimestamp())
                        .sessionId(chat.getSessionId())
                        .build())
                .collect(Collectors.toList());
    }

}
