package example.day03;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class ChatSocketHandler extends TextWebSocketHandler {

    // room 별 접속자 관리
    private static final Map<String, List<WebSocketSession>> 접속명단 = new Hashtable<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    // 1. 클라이언트 접속 성공 시
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("클라이언트 접속 성공: " + session);
        // 방 입장은 클라이언트에서 "join" 메시지를 보냈을 때 처리
    }

    // 2. 클라이언트 메시지 수신 시
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        Map<String, String> map = objectMapper.readValue(payload, Map.class);

        String type = map.get("type");

        if ("join".equals(type)) {

            System.out.println( map );

            String room = String.valueOf( map.get("room") ) ;
            String nickName = map.get("nickName");

            // 세션 속성에 저장
            session.getAttributes().put("room", room);
            session.getAttributes().put("nickName", nickName);
            System.out.println("========================");
            System.out.println( session.getAttributes() );

            // 접속명단에 등록
            if (!접속명단.containsKey(room)) {
                List<WebSocketSession> sessions = new ArrayList<>();
                sessions.add(session);
                접속명단.put(room, sessions);
            } else {
                접속명단.get(room).add(session);
            }

            // System.out.println(nickName + "님이 " + room + "에 입장했습니다.");
            alarmMessage( room , nickName + "님이 입장했습니다."  );

        } else {
            // 일반 메시지 브로드캐스팅
            String room = (String) session.getAttributes().get("room");
            if (room != null && 접속명단.containsKey(room)) {
                List<WebSocketSession> list = 접속명단.get(room);
                for (WebSocketSession client : list) {
                    client.sendMessage(message);
                }
            }
        }
    }

    // 3. 클라이언트 접속 해제 시
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("클라이언트 접속 해제: " + session);
        String room = (String) session.getAttributes().get("room");
        String nickName = (String) session.getAttributes().get("nickName");
        if (room != null && nickName != null) {
            // 세션 제거
            List<WebSocketSession> list = 접속명단.get(room);
            list.remove(session);
            alarmMessage( room , nickName + "님이 퇴장했습니다."  );
        }
    }

    // ++
    public void alarmMessage( String room , String message ) throws Exception {
        // 입장 알림 메시지 전송
        Map<String, Object> msg = new HashMap<>();
        msg.put("type", "alarm");
        msg.put("message", message );
        //msg.put("message", nickName + "님이 입장했습니다.");
        String json = objectMapper.writeValueAsString(msg);
        List<WebSocketSession> list = 접속명단.get(room);
        for (WebSocketSession client : list) {
            client.sendMessage(new TextMessage(json));
        }
    }

}