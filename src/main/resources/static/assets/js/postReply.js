import { bno } from "./getReply.js";
import { BASE_URL } from "./reply.js";
import { renderReplies } from "./getReply.js";

export function registerReply() {
    fetch(BASE_URL, {
        method: "POST",
        headers: {
            "content-type": "application/json",
        },
        body: JSON.stringify({
            text: document.querySelector("#newReplyText").value,
            author: document.querySelector("#newReplyWriter").value,
            bno: bno,
        }),
    })
        .then((res) => res.json())
        .then((json) => {
            renderReplies(json);
            document.querySelector("#newReplyText").value = "";
            document.querySelector("#newReplyWriter").value = "";
        });
}
