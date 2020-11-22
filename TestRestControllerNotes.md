# Test Rest Controllers Notes
### Curl
- Получить список всей еды для текущего пользователя   
  `curl -v http://localhost:8080/topjava/rest/meals/all`
- Получить определенную еду с ID для текущего пользователя с определенным идентификатором. Например, ID=100002 
 `curl -v http://localhost:8080/topjava/rest/meals/100002`
- Обновить определенную еду с ID для текущего пользователя. Например, ID=100002
`curl -X PUT -H "Content-Type: application/json" -d "{\"id\":100002,\"dateTime\":\"2020-01-30T08:00:00\",\"description\":\"Morning meal\",\"calories\":600,\"user\":null}" -v http://localhost:8080/topjava/rest/meals/100002`
- Удалить определенную еду с ID у текущего пользователя. Например, ID=100013
`curl -X DELETE -v http://localhost:8080/topjava/rest/meals/100013`
- Создать еду для текущего пользователя
`curl -X POST -H "Content-Type: application/json" -d "{\"dateTime\":\"2020-03-30T08:00:00\",\"description\":\"Morning meal\",\"calories\":600}" -v http://localhost:8080/topjava/rest/meals`

