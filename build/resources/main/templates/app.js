document.addEventListener("DOMContentLoaded", () => {
    const socket = new WebSocket("ws://localhost:8080/ws");

    socket.addEventListener("open", () => {
        console.log("Connected to WebSocket");
    });

    socket.addEventListener("message", (event) => {
        console.log("Message received:", event.data);
        document.getElementById("response").innerText = "Received: " + event.data;
    });

    socket.addEventListener("close", () => {
        console.log("Disconnected from WebSocket");
    });

    socket.addEventListener("error", (error) => {
        console.error("WebSocket error:", error);
    });

    document.getElementById("sendButton").addEventListener("click", () => {
        const toMemberId = document.getElementById("toMemberId").value;
        const message = document.getElementById("message").value;

        if (toMemberId && message) {
            const payload = {
                toMemberId: toMemberId,
                fromMemberUuid: "your-uuid", // Replace with actual UUID or handle as needed
                message: message
            };

            socket.send(JSON.stringify(payload));
        } else {
            alert("Please enter recipient ID and message");
        }
    });
});