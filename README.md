# spring-sql

Spring에서 Database를 다루는 다양한 방법들에 대해서 알아보고, 각 방법 별 성능을 테스트해봅니다.

## Insert

Insert를 하는 방법 중, 3가지 라이브러리인 JPA, Mybatis, JdbcTemplate을 활용해 각각 비교했습니다.

그 결과는 아래와 같습니다.

| 이름 | n = 10 | n = 1000 | n = 10000 | n = 10\_0000 | n = 50\_0000 |
| --- | --- | --- | --- | --- | --- |
| DataJpa Save | 70 | 203 | 696 | 2767 | 10754 |
| DataJpa SaveAll | 71 | 192 | 598 | 2604 | 10718 |
| DataJpa SaveAndFlush | 107 | 378 | 8737 | 950177 | ♾ |
| DataJpa SaveAllAndFlush | 112 | 388 | 8701 | ♾ | ♾ |
| EntityManager Persist | 65 | 162 | 586 | 2446 | 9474 |
| QueryDSL insert | 132 | 194 | 581 | 1295 | 3255 |
| Mybatis XML Insert | 27 | 53 | 120 | 513 | 1065 |
| Mybatis Annotation Insert | 24 | 51 | 100 | 437 | 1020 |
| Mybatis Provider Insert | 33 | 73 | 170 | 637 | 1601 |
| JdbcTemplate update | 14 | 45 | 87 | 341 | 784 |
| SimpleJdbcInsert | 30 | 45 | 98 | 372 | 925 |
| PreparedStatatementCreator | 12 | 40 | 98 | 356 | 845 |
| NamedParameterJdbcTemplate Map | 17 | 51 | 106 | 410 | 1036 |
| NamedParameterJdbcTemplate BeanPropertySqlParameterSource | 21 | 58 | 125 | 476 | 1060 |
| JdbcTemplate batchUpdate | 16 | 35 | 66 | 279 | 663 |
| NamedParameterJdbcTemplate batchUpdate | 19 | 39 | 95 | 331 | 908 |

![](https://velog.velcdn.com/images/kgh2120/post/e4fcf191-adf0-42de-b844-3fa2344058d9/image.png)

조금 더 자세한 설명은 아래 링크를 통해 확인해주세요.

[[Spring] INSERT를 하는 다양한 방법 비교](https://dev-qhyun.tistory.com/23)
