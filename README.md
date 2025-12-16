# cp_ollama_springboot
Ollama Webservice

Install Ollama on Linux

> $curl -fsSL https://ollama.com/install.sh | sh

default model path
/usr/share/ollama/.ollama/models/

Run Ollama with a model

> $ollama run 7shi/llama-translate:8b-q4_K_M


curl Testing

> $curl http://localhost:11434/api/generate -d '{
"model": "7shi/llama-translate:8b-q4_K_M",
"prompt": "Why is the sky blue? translate to Chinese"
}'