package example.day17.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatPublisherController {

    private final StringRedisTemplate redisTemplate;
    private final ChannelTopic chatTopic;

    @PostMapping("/send")
    public String send(@RequestBody ChatMessage msg) {
        String payload = String.format("[%s] %s : %s",
                msg.getTime(), msg.getSender(), msg.getMessage());
        redisTemplate.convertAndSend(chatTopic.getTopic(), payload);
        return "메시지 발행: " + payload;
    }
}