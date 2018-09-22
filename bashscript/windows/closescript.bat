FOR /f "tokens=5" %%a IN ('netstat -ano ^| findstr 0.0.0.0:7090') DO (taskkill /f /pid %%a) & FOR /f "tokens=5" %%a IN ('netstat -ano ^| find "4210" ^| find "LISTENING"') DO taskkill /f /pid %%a
exit