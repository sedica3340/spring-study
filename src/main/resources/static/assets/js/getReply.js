import { BASE_URL } from "./reply.js";
import { showSpinner, hideSpinner } from "./spinner.js";
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

function renderPage({ begin, end, prev, next, pageInfo, finalPage }) {
    let tag = "";
    if (pageInfo.pageNo >= 2) {
        tag += `<li class='page-item'><a class='page-link page-custom' href='${1}'>\<\<</a></li>`;
    }
    if (prev) {
        tag += `<li class='page-item'><a class='page-link page-custom' href='${
            begin - 1
        }'>prev</a></li>`;
    }
    for (let i = begin; i <= end; i++) {
        let active = "";
        if (pageInfo.pageNo === i) active = "p-active";
        tag += `<li class='page-item ${active}'><a class='page-link page-custom' href='${i}'>${i}</a></li>`;
    }
    if (next) {
        tag += `<li class='page-item'><a class='page-link page-custom' href='${
            end + 1
        }'>next</a></li>`;
    }
    if (pageInfo.pageNo < finalPage) {
        tag += `<li class='page-item'><a class='page-link page-custom' href='${finalPage}'>\>\></a></li>`;
    }
    // 페이지 태그 ul에 붙이기
    const $pageUl = document.querySelector(".pagination");
    $pageUl.innerHTML = tag;
    $pageUl.dataset.page = pageInfo.pageNo;
}

export function renderReplies({ pageInfo, replies }) {
    document.querySelector("#replyCnt").textContent = pageInfo.totalCount;

    let tag = "";
    if (replies && replies.length > 0) {
        replies.forEach(({ rno, writer, text, createAt }) => {
            tag += `
        <div id='replyContent' class='card-body' data-reply-id='${rno}'>
        <div class='row user-block'>
        <span class='col-md-3'>
        <b>${writer}</b>
        </span>
        <span class='offset-md-6 col-md-3 text-right'><b>${getDate(
            createAt
        )}</b></span>
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

    // 페이지 태그 렌더링더링더링더링더링더링
    // renderPage(pageInfo);
}

export async function fetchReplies(pageNo = 1) {
    const res = await fetch(`${BASE_URL}/${bno}/page/${pageNo}`);
    const replyResponse = await res.json();
    // 댓글 목록 렌더링
    renderReplies(replyResponse);
}

export function replyPageClickEvent() {
    document.querySelector(".pagination").addEventListener("click", (e) => {
        e.preventDefault();
        if (!e.target.matches(".page-link")) return;
        const link = e.target.getAttribute("href");
        fetchReplies(link);
    });
}

// 무한 스크롤 전용 함수////////////////////
let currentPage = 1; // 현재 스크롤시 진행되고있는 페이지번호
let isFetching = false; // 데이터를 불러오는 중에는 이벤트발생을 제어하기 위한 논리변수
let totalReplies = 0; // 총 댓글 수
let loadedReplies = 0; // 로딩된 댓글 수
let replyCount = 1;
export function appendReplies({ pageInfo, replies }) {
    let tag = "";
    if (replies && replies.length > 0) {
        replies.forEach(({ rno, writer, text, createAt }) => {
            tag += `
            <div id='replyContent' class='card-body' data-reply-id='${rno}'>
            <div class='row user-block'>
            <span class='col-md-3'>
            <b>${replyCount++}. ${writer}</b>
            </span>
            <span class='offset-md-6 col-md-3 text-right'><b>${getDate(
                createAt
            )}</b></span>
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
        if (totalReplies <= loadedReplies) return;
        tag = `<div id='replyContent' class='card-body'>댓글이 아직 없습니다! ㅠㅠ</div>`;
    }
    document.querySelector("#replyData").innerHTML += tag;
    loadedReplies += replies.length;

    // 로드된 댓글 수 업데이트
}

export async function fetchInfScrollReplies(pageNo = 1) {
    if (isFetching) return;

    isFetching = true;

    const bno = document.querySelector("#wrap").dataset.bno; // 게시물 글번호

    const res = await fetch(`${BASE_URL}/${bno}/page/${pageNo}`);
    const replyResponse = await res.json();

    if (pageNo === 1) {
        // 총 댓글 수 전역변수 값 세팅
        totalReplies = replyResponse.pageInfo.totalCount;
        loadedReplies = 0; // 1페이지 다시 로딩시 초기값으로 만들어주기 비동기오류 대비
        replyCount = 1;
        document.querySelector("#replyCnt").textContent = totalReplies;
        // 초기댓글 reset
        document.querySelector("#replyData").innerHTML = "";
        isFetching = false;
        setupInfiniteScroll();

        hideSpinner();
    }
    // 댓글을 전부 가져올 시 스크롤 이벤트 제거하기
    if (loadedReplies >= totalReplies) {
        window.removeEventListener("scroll", scrollHandler);
    }

    // console.log(replyResponse);
    appendReplies(replyResponse);
    currentPage = pageNo;
    isFetching = false;
    hideSpinner();
}

async function scrollHandler(e) {
    // 스크롤이 최하단부로 내려갔을 때만 이벤트를 발동시키도록 해야함
    // 현재창에 보이는 세로길이 + 스크롤을 내린 길이 >= 브라우저 전체 세로길이
    if (
        window.innerHeight + window.scrollY >=
            document.body.offsetHeight + 150 &&
        !isFetching
    ) {
        // console.log(e);
        // 서버에서 데이터를 비동기로 불러와
        // 1초의 대기열이 생성되면 1초 뒤에 새 대기열이 생성되도록함
        if (totalReplies > loadedReplies) {
            showSpinner();
        }
        await new Promise((resolve) => {
            setTimeout(resolve, 500);
        });
        fetchInfScrollReplies(currentPage + 1);
    }
}

export function setupInfiniteScroll() {
    window.addEventListener("scroll", scrollHandler);
}
