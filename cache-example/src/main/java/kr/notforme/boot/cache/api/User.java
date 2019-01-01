package kr.notforme.boot.cache.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User
        implements Serializable {
    private static final long serialVersionUID = 2832501217596964278L;

    private int id;
    private String name;
    private int age;
}
