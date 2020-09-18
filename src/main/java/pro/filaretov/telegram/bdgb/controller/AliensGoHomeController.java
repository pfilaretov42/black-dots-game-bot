package pro.filaretov.telegram.bdgb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.filaretov.telegram.bdgb.bot.ScoreManager;
import pro.filaretov.telegram.bdgb.dto.ScoreDto;

/**
 * Controller for AGH game.
 */
@RestController
@RequestMapping("/api/v1/agh")
@RequiredArgsConstructor
public class AliensGoHomeController {

    private final ScoreManager scoreManager;

    @PostMapping("/score")
    // TODO - return something?
    public void setScore(@RequestBody ScoreDto scoreDto) {
        // TODO - check whether the request is allowed or not, so that cheating is not possible

        Integer userId = scoreDto.getUserId();
        String chatId = scoreDto.getChatId();
        Integer score = scoreDto.getScore();
        scoreManager.setScore(userId, chatId, score);
    }

}
