import { fetchReplies } from "./getReply.js";
import { registerReply } from "./postReply.js";
import { replyPageClickEvent } from "./getReply.js";
import { replyDeleteEvent } from "./deleteReply.js";
import { setupInfiniteScroll } from "./getReply.js";
import { fetchInfScrollReplies } from "./getReply.js";
// ===== 전역 변수 =====
export const BASE_URL = "http://localhost:8383/api/v1/replies";
const $btn = document.querySelector("#replyAddBtn");
// ===== 함수 정의 =====

// ===== 실행 코드 =====
// fetchReplies();
// 스크롤 이벤트
fetchInfScrollReplies(); // 일단 1페이지 데이터 그려놓기
setupInfiniteScroll();

$btn.addEventListener("click", (e) => {
    registerReply();
});
document.querySelector("#newReplyWriter").addEventListener("keyup", (e) => {
    if (!(e.key === "Enter")) return;
    $btn.click();
});

// 페이지 클릭 이벤트
// replyPageClickEvent();

document.querySelector("#replyData").addEventListener("click", (e) => {
    replyDeleteEvent(e);
});