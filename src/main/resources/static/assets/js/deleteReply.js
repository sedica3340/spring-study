import { BASE_URL } from "./reply.js";
import { fetchInfScrollReplies, renderReplies } from "./getReply.js";
import { appendReplies } from "./getReply.js";
import { replyModifyEvent } from "./modify.js";
export function replyDeleteEvent(e) {
    e.preventDefault();
    if (e.target.matches("#replyModBtn")) replyModifyEvent(e);
    if (!e.target.matches("#replyDelBtn")) return;
    if (!confirm("정말 삭제할까요?")) return;

    const delTarget = e.target.closest("#replyContent").dataset.replyId;
    console.log(delTarget);
    fetch(`${BASE_URL}/${delTarget}`, {
        method: "DELETE",
    }).then(() => {
        fetchInfScrollReplies(1);
        if (window.scrollY >= 820) {
            window.scrollTo(0, 820);
        }
    });
}
