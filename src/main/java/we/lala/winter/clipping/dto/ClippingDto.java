package we.lala.winter.clipping.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ClippingDto {

    private String textMessage;

    private String clippedUrl;

    private Boolean checked;

    private Integer numbering;

    private Date modifiedDt;
}
