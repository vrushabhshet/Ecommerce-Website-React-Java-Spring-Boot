import React from 'react';
import {faInfoCircle} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

function Contacts() {
    return (
        <div className="container mt-5">
            <h4><FontAwesomeIcon className="ml-2 mr-2" icon={faInfoCircle}/>Contacts</h4>
            <br/>
            <p><b>Mobile:</b> +1 (312) 371-0915<br/>
                <b>E-mail:</b>vrushabhshetcr7@gmail.com</p>
            <br/>
            <h5>Working time</h5>
            <p>
                💻 &nbsp; Online orders are accepted <strong>24/7</strong> — shop anytime, anywhere! <br/>
                🚚 &nbsp; Fast & reliable delivery right to your doorstep. <br/>
                🔒 &nbsp; Secure payments and hassle-free checkout. <br/>
                📞 &nbsp; Customer support available during business hours to assist you.
            </p>
            <br/>
            <h5>Delivery</h5>
            <p>Delivery of orders come through courier service to you respective addresses.</p>
        </div>
    )
}

export default Contacts