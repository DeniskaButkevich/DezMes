package dez.DezMes.Controller;

import com.fasterxml.jackson.annotation.JsonView;
import dez.DezMes.domain.Message;
import dez.DezMes.domain.Views;
import dez.DezMes.dto.EventType;
import dez.DezMes.dto.ObjectType;
import dez.DezMes.repo.MessageRepo;
import dez.DezMes.util.WsSender;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("message")
public class MessageController {
    private final MessageRepo messageRepo;
    private final BiConsumer<EventType, Message> wsSender;

    @Autowired
    public MessageController(MessageRepo messageRepo, WsSender wsSender) {
        this.messageRepo = messageRepo;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.IdName.class);
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Message> list() {
        return messageRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getOne(@PathVariable Long id) {
        Message message = messageRepo.findById(id).get();
        return message;
    }

    @PostMapping
    public Message create(@RequestBody Message message) {
        message.setCreationDate(LocalDateTime.now());
        Message updatedMessage = messageRepo.save(message);

        wsSender.accept(EventType.CREATE, updatedMessage);

        return messageRepo.save(message);
    }

    @PutMapping("{id}")
    public Message update(
            @PathVariable("id") Long id,
            @RequestBody Message message
    ) {
        Message messageFromDb = messageRepo.findById(id).get();

        BeanUtils.copyProperties(message, messageFromDb, "id");

        Message updatedMessage = messageRepo.save(messageFromDb);

        wsSender.accept(EventType.UPDATE, updatedMessage);

        return messageRepo.save(messageFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") String id) {
        Message message = messageRepo.findById(Long.parseLong(id)).get();
        wsSender.accept(EventType.REMOVE, message);
    }
}