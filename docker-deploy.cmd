
call mvn clean package
call mvn clean install
call docker build --tag=xevi8x/bulb_gate:latest .
call docker push xevi8x/bulb_gate