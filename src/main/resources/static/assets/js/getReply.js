import { BASE_URL } from "./reply.js";

export const bno = document.querySelector("#wrap").dataset.bno;

function getDate(createAt) {
    const now = new Date();
    const past = new Date(createAt);
    const second = (now - past) / 1000;
    let time = Math.floor(second) + "초 전";
    if (second >= 60 && second < 3600) {
        time = Math.floor(second / 60) + "분 전";
    } else if (second >= 3600 && second < 86400) {
        time = Math.floor(second / 3600) + "시간 전";
    } else if (second >= 86400) {
        time = Math.floor(second / 86400) + "일 전";
    }
    return time;
}

export function renderReplies(replies) {
    document.querySelector("#replyCnt").textContent = replies.length;

    let tag = "";
    if (replies && replies.length > 0) {
        replies.forEach(({ rno, writer, text, createAt }) => {
            tag += `
        <div id='replyContent' class='card-body' data-reply-id='${rno}'>
        <div class='row user-block'>
        <span class='col-md-3'>
        <b>${writer}</b>
        </span>
        <span class='offset-md-6 col-md-3 text-right'><b>${getDate(createAt)}</b></span>
        </div><br>
        <div class='row'>
        <div class='col-md-9'>${text}</div>
        <div class='col-md-3 text-right'>
        <a id='replyModBtn' class='btn btn-sm btn-outline-dark' data-bs-toggle='modal' data-bs-target='#replyModifyModal'>수정</a>&nbsp;
        <a id='replyDelBtn' class='btn btn-sm btn-outline-dark' href='#'>삭제</a>
        </div>
        </div>
        </div>
        `;
        });
    } else {
        tag = `<div id='replyContent' class='card-body'>댓글이 아직 없습니다! ㅠㅠ</div>`;
    }
    document.querySelector("#replyData").innerHTML = tag;
}

export async function fetchReplies() {
    const res = await fetch(`${BASE_URL}/${bno}`);
    const replies = await res.json();
    // 댓글 목록 렌더링
    renderReplies(replies);
}
