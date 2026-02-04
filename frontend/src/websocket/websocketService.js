import stompClient from './stompClient';

let connectedCallback = null;

export function connectWebSocket(onMessageReceived) {
    stompClient.onConnect = () => {
        console.log('✅ WebSocket connected');

        stompClient.subscribe('/topic/notify', (message) => {
            const payload = JSON.parse(message.body);
            console.log('📩 Received:', payload);
            if (onMessageReceived) onMessageReceived(payload);
        });

        if (connectedCallback) connectedCallback();
    };

    stompClient.onStompError = (frame) => {
        console.error('❌ STOMP error:', frame);
    };

    stompClient.activate();
}

export function sendMessage(destination, payload) {
    if (stompClient.connected) {
        stompClient.publish({
            destination: destination, // e.g. "/app/hello"
            body: JSON.stringify(payload),
        });
    } else {
        console.warn('⚠️ STOMP not connected');
    }
}

export function onConnected(callback) {
    connectedCallback = callback;
}
