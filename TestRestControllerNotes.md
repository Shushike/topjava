# Test Rest Controllers Notes
### Curl
- Get list of all meal for current user    
 
    `curl -v http://localhost:8080/topjava/rest/meals`
- Get certain meal with ID for current user. For example, ID=100002 
 
    `curl -v http://localhost:8080/topjava/rest/meals/100002`
- Update certain meal with ID for current user. For example, ID=100002

    `curl -X PUT -H "Content-Type: application/json" -d "{\"id\":100002,\"dateTime\":\"2020-01-30T08:00:00\",\"description\":\"Morning meal\",\"calories\":600,\"user\":null}" -v http://localhost:8080/topjava/rest/meals/100002`
- Delete certain meal with ID for current user. For example, ID=100013

    `curl -X DELETE -v http://localhost:8080/topjava/rest/meals/100013`
- Create meal for current user
    
    `curl -X POST -H "Content-Type: application/json" -d "{\"dateTime\":\"2020-03-30T08:00:00\",\"description\":\"Morning meal\",\"calories\":600}" -v http://localhost:8080/topjava/rest/meals`
    
 

