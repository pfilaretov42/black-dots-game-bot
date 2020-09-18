package pro.filaretov.telegram.bdgb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Game score DTO.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ScoreDto {

    private Integer userId;
    private String chatId;
    private Integer score;
}
