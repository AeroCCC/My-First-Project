import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import Stomp from "stompjs";

const socket = new SockJS('ws://localhost:8080/ws');
const stompClient = Stomp.over(socket);

const stompClient = new Client({
    webSocketFactory: () => socket,
    reconnectDelay: 5000,
    debug: (str) => console.log('[STOMP]', str),
});

export default stompClient;
