import { bno } from "./getReply.js";
import { BASE_URL } from "./reply.js";
import { renderReplies } from "./getReply.js";
import { fetchInfScrollReplies } from "./getReply.js";

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
    }).then(() => {
        fetchInfScrollReplies(1);
        if (window.scrollY >= 820) {
            window.scrollTo(0, 820);
        }
        document.querySelector("#newReplyText").value = "";
        document.querySelector("#newReplyWriter").value = "";
    });
}
