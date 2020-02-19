package we.lala.winter.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class Memo {

    private Long id;

    private String text;

    private boolean check;

    private Date createDt;

    private Date modifiedDt;
}
