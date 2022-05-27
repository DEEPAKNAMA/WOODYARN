const paymentStart = () =>{
    let amount = $("#amount").val();
    let address= $("#address").val();

    $.ajax({
        url:'/shop/buy/orderc',
        data:JSON.stringify({amount:amount,info:'order_request',address:address}),
        contentType:'application/json',
        type:'POST',
        dataType:'json',
        success:function(response){
            console.log(response)
            if(response.status=="created"){
                let options={
                    key:'rzp_live_eCZv8Rpc6rj3OA',
                    amount:response.amount,
                    currency:'INR',
                    name:'Wood & Yarn',
                    description:'Order Payment',
                    image:'https://i.ebayimg.com/images/g/DpUAAOSw1UVc8xsN/s-l400.jpg',
                    order_id:response.id,
                    handler:function(response){
                        updatePaymentInServer(response.razorpay_payment_id,response.razorpay_order_id,"paid");
                    },
                    "prefill": {
                        "name": "",
                        "email": "",
                        "contact": ""
                    },
                    "notes": {
                        "address": "Wood & Yarn"
                    },
                    "theme": {
                        "color": "#3399cc"
                    }
                };


                var rzp = new Razorpay(options); 
                rzp.on('payment.failed', function (response){
                        alert(response.error.code);
                        alert(response.error.description);
                        alert(response.error.source);
                        alert(response.error.step);
                        alert(response.error.reason);
                        alert(response.error.metadata.order_id);
                        alert(response.error.metadata.payment_id);
                });

                rzp.open();
            }
        },
        error:function(error){ 
            console.log(error)
        }
        
    })
};


function updatePaymentInServer(payment_id,order_id,order_status){
    $.ajax({
        url:'/shop/corder',
        data:JSON.stringify({payment_id:payment_id,order_id:order_id,order_status:order_status}),
        contentType:'application/json',
        type:'POST',
        dataType:'json',
        success:function(response){
            swal("Good job!", "Payment success! ", "success");
            var delayInMilliseconds = 2000;

            setTimeout(function() {
              window.location.href = "/orders";
            }, delayInMilliseconds);

        },
        error:function(error){ 
            console.log(error)
        }
    })
}



