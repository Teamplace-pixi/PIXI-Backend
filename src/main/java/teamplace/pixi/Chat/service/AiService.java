package teamplace.pixi.Chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamplace.pixi.Chat.dto.AiChatRequest;
import teamplace.pixi.Chat.dto.AiChatResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class AiService {

    private static final String FASTAPI_URL = "http://localhost:8000/chat";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AiChatResponse sendToFastApi(AiChatRequest dto) {
        try {
            // 1. URL 연결
            URL url = new URL(FASTAPI_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            // 2. JSON 문자열 작성
            String jsonInputString = objectMapper.writeValueAsString(dto);

            // 3. 요청 본문에 JSON 데이터 쓰기
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
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

            // 5. JSON 응답을 AiChatResponse로 변환
            return objectMapper.readValue(response.toString(), AiChatResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
            return new AiChatResponse("AI 서버 연결 실패", null);
        }
    }
}
