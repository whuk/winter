package we.lala.winter.domain;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Memo {

    private Long id;

    private String text;

    private boolean check;

    private Date createDt;

    private Date modifiedDt;
}
