package capstone.Antiheimer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class RecentDateRes {

    private String statusCode;
    private String message;
    private LocalDate date;

    public RecentDateRes(String statusCode, String message, LocalDate date) {
        this.statusCode = statusCode;
        this.message = message;
        this.date = date;
    }
}
