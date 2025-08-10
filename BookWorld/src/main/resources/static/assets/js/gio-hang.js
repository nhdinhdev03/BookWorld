function refreshCart(item, iStat){
    item.quantity = $(".input-number")[iStat.index].value;
    $.ajax({
        type: "POST",
        url: "/cart/item/update",
        data: JSON.stringify(item),
        contentType: "application/json",
        dataType: "json",
        success: function(resp) {
            $(".td-giaTong")[iStat.index].textContent = resp.book.price * resp.quantity;
        },
        error: function(error) {
            console.log(error);
        }
    });
}
// function removeCart(item,iStat){
//     var choice = confirm("Bạn có chắc muốn xóa?");
//     if(choice == false) {
//         return;
//     }
//     $.ajax({
//         type: "POST",
//         url: "/cart/item/remove",
//         data: JSON.stringify(item),
//         contentType: "application/json",
//         dataType: "text",
//         success: function(resp) {
//             alert(resp)
//             $(".basket-product").eq(iStat.index).hide();
//         },
//         error: function(error) {
//             console.log(error);
//         }
//     });
// }

// function removeCartAll(){
//     var choice = confirm("Bạn có chắc muốn xóa tất cả?");
//     if(choice == false) {
//         return;
//     }
//     $.ajax({
//         type: "POST",
//         url: "/cart/item/removeall",
//         dataType: "text",
//         success: function(resp) {
//             alert(resp)
//             $(".basket-product").hide();
//         },
//         error: function(error) {
//             console.log(error);
//         }
//     });
// }
