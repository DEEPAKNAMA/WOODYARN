const paymentStart = () =>{
    let amount = $("#amount").val();
    console.log(amount);

    $.ajax({
        url:'/shop/buy/orderc',
        data:JSON.stringify({amount:amount,info:'order_request'}),
        contentType:'application/json',
        type:'POST',
        dataType:'json',
        success:function(response){
            console.log(response)
            if(response.status=="created"){
                let options={
                    key:'rzp_test_Upla1xiTApXSWX',
                    amount:response.amount,
                    currency:'INR',
                    name:'Wood & Yarn',
                    description:'Order Payment',
                    image:'https://images.unsplash.com/photo-1453728013993-6d66e9c9123a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8dmlld3xlbnwwfHwwfHw%3D&w=1000&q=80',
                    order_id:response.id,
                    handler:function(response){
//                        console.log(response.razorpay_payment_id)
//                        console.log(response.razorpay_order_id)
//                        console.log(response.razorpay_signature)
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
            var delayInMilliseconds = 3000; //1 second

            setTimeout(function() {
              window.location.href = "/orders";
            }, delayInMilliseconds);

        },
        error:function(error){ 
            console.log(error)
        }
    })
}


var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
  return new bootstrap.Tooltip(tooltipTriggerEl)
})
