import { fetchReplies } from "./getReply.js";
import { registerReply } from "./postReply.js";
// ===== 전역 변수 =====
export const BASE_URL = "http://localhost:8383/api/v1/replies";
const $btn = document.querySelector("#replyAddBtn");
// ===== 함수 정의 =====

// ===== 실행 코드 =====
fetchReplies();

$btn.addEventListener("click", (e) => {
    registerReply();
});
document.querySelector("#newReplyWriter").addEventListener("keyup", (e) => {
    if (!(e.key === "Enter")) return;
    $btn.click();
});

