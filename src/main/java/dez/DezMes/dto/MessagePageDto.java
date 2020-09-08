package dez.DezMes.dto;

import com.fasterxml.jackson.annotation.JsonView;
import dez.DezMes.domain.Message;
import dez.DezMes.domain.Views;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonView(Views.FullMessage.class)
public class MessagePageDto {
    private List<Message> messages;
    private int currentPage;
    private int totalPages;
}
