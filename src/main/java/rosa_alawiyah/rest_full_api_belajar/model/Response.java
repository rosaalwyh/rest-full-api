package rosa_alawiyah.rest_full_api_belajar.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Response<T> {
    private T data;

    private String message;

    @JsonProperty("error_messages")
    private String errorMessages;


}
