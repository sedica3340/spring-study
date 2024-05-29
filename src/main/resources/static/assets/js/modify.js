export function replyModifyEvent(e) {
    const currentText = e.target.closest(".row").firstElementChild.textContent;
    console.log(currentText);
    document.querySelector("#modReplyText").value = currentText;
}