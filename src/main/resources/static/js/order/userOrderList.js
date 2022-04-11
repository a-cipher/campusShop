/**
 * 订单操作
 * @param orderId 订单编号
 * @constructor
 */
const OrderOperational = function (orderId) {
    console.log("订单id = " + orderId);
    $.actions({
        actions: [{
            text: "取消订单",
            className: "color-success",
            onClick: function () {
                console.log("取消按钮！");
                $.prompt({
                    title: '取消',
                    text: '确认取消订单',
                    input: '',
                    empty: true, // 是否允许为空
                    onOK: function (input) {
                        //点击确认
                        console.log("确认提交订单，商家备注信息：" + input.valueOf());
                        $.ajax({
                            type: "post",
                            url: "/order/userRejectOrder",
                            dataType: "json",
                            data: {
                                orderId: orderId,
                                message: input.valueOf()
                            },
                            success: function (rtn) {
                                console.log(rtn);
                                if (rtn.status === 200) {
                                    $.toast("操作成功");
                                    let $1 = $("#orderStatus_" + orderId);
                                    $1.empty();
                                    const t = '<span>' + rtn.data.orderStatus + '</span>';
                                    $1.append(t);
                                    $("#orderShop_" + orderId).text(rtn.data.shopRemark);
                                } else {
                                    $.toast("操作失败", "forbidden");
                                    let $1 = $("#orderStatus_" + orderId);
                                    $1.empty();
                                    const t = '<span>' + rtn.data.orderStatus + '</span>';
                                    $1.append(t);
                                    $("#orderShop_" + orderId).text(rtn.data.shopRemark);
                                }
                            },
                            error: function (xhr) {
                                console.log("错误提示： " + xhr + " ---- " + xhr.status + " " + xhr.statusText);
                            },
                            //请求完成后回调函数 (请求成功或失败之后均调用)。参数： XMLHttpRequest 对象和一个描述成功请求类型的字符串
                            complete: function (XMLHttpRequest, textStatus) {
                                console.log("函数调用完成，将按钮设置为可用状态");
                                // 请求完成，将按钮重置为可用
                            }
                        });
                    },
                    onCancel: function () {
                        //点击取消
                    }
                });
            }
        }]
    });
};

/**
 * 订单显示操作
 * @constructor
 */
const OrderListOperational = function () {
    // const userId = getUrlParam("userId");
    $.actions({
        actions: [{
            text: "全部订单",
            onClick: function () {
                //do something
                window.location.href = "/order/listUserOrder";
            }
        }, {
            text: "处理中订单",
            onClick: function () {
                //do something
                window.location.href = "/order/listUserOrder?flag=0";
            }
        }, {
            text: "成功订单",
            onClick: function () {
                //do something
                window.location.href = "/order/listUserOrder?flag=1";
            }
        }, {
            text: "失败订单",
            onClick: function () {
                //do something
                window.location.href = "/order/listUserOrder?flag=2";
            }
        }]
    });
};