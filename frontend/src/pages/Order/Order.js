import React, {Component} from 'react';
import PropTypes from "prop-types";
import {connect} from "react-redux";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCheckCircle, faShoppingBag} from "@fortawesome/free-solid-svg-icons";

import {IMG_URL} from "../../utils/constants/url";
import {fetchOrder, addOrder} from "../../actions/order-actions";
import {validateEmail} from "../../utils/input-validators";

class Order extends Component {
    state = {
        firstName: "",
        lastName: "",
        city: "",
        address: "",
        postIndex: "",
        phoneNumber: "",
        email: "",
        validateEmailError: "",
        paymentStep: false,
        paymentSuccess: false
    };

    componentDidMount() {
        this.props.fetchOrder();
    }

    onFormSubmit = (event) => {
        event.preventDefault();

        let totalPrice = 0;
        this.props.products.map((product) => totalPrice = totalPrice + product.price);

        const {firstName, lastName, city, address, postIndex, phoneNumber, email} = this.state;
        const validateEmailError = validateEmail(email);

        if (validateEmailError) {
            this.setState({
                validateEmailError
            });
        } else {
            // Proceed to payment step
            this.setState({
                paymentStep: true
            });
        }
    };

    handlePay = () => {
        // Simulate payment success
        this.setState({
            paymentSuccess: true
        });
        setTimeout(() => {
            this.setState({ paymentStep: false });
        }, 1000);
    };

    handleOrderConfirm = () => {
        let totalPrice = 0;
        this.props.products.map((product) => totalPrice = totalPrice + product.price);
        const productList = this.props.products;
        const {firstName, lastName, city, address, postIndex, phoneNumber, email} = this.state;
        const order = {firstName, lastName, city, address, postIndex, phoneNumber, email, productList, totalPrice};
        this.props.addOrder(order, this.props.history);
    };

    handleInputChange = (event) => {
        const {name, value} = event.target;

        this.setState({
            [name]: value
        });
    };

    render() {
    const {products} = this.props;
    const {firstName, lastName, city, address, postIndex, phoneNumber, email, validateEmailError, paymentStep, paymentSuccess} = this.state;
        const {firstNameError, lastNameError, cityError, addressError, postIndexError, phoneNumberError, emailError} = this.props.errors;

        let totalPrice = 0;
    products.map((product) => totalPrice = totalPrice + product.price);

        return (
            <div className="container mt-5 pb-5">
                <h4 className="mb-4 text-center">
                    <FontAwesomeIcon className="mr-2" icon={faShoppingBag}/> Ordering
                </h4>
                <br/>
                {!paymentStep && !paymentSuccess && (
                    <form onSubmit={this.onFormSubmit}>
                        <div className="row">
                            <div className="col-lg-6">
                                <div className="form-group row">
                                    <label className="col-sm-2 col-form-label">Name:</label>
                                    <div className="col-sm-8">
                                        <input
                                            type="text"
                                            className={firstNameError ? "form-control is-invalid" : "form-control"}
                                            name="firstName"
                                            value={firstName}
                                            placeholder="Enter the first name"
                                            onChange={this.handleInputChange}/>
                                        <div className="invalid-feedback">{firstNameError}</div>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label className="col-sm-2 col-form-label">Surname:</label>
                                    <div className="col-sm-8">
                                        <input
                                            type="text"
                                            className={lastNameError ? "form-control is-invalid" : "form-control"}
                                            name="lastName"
                                            value={lastName}
                                            placeholder="Enter the last name"
                                            onChange={this.handleInputChange}/>
                                        <div className="invalid-feedback">{lastNameError}</div>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label className="col-sm-2 col-form-label">City:</label>
                                    <div className="col-sm-8">
                                        <input
                                            type="text"
                                            className={cityError ? "form-control is-invalid" : "form-control"}
                                            name="city"
                                            value={city}
                                            placeholder="Enter the city"
                                            onChange={this.handleInputChange}/>
                                        <div className="invalid-feedback">{cityError}</div>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label className="col-sm-2 col-form-label">Address:</label>
                                    <div className="col-sm-8">
                                        <input
                                            type="text"
                                            className={addressError ? "form-control is-invalid" : "form-control"}
                                            name="address"
                                            value={address}
                                            placeholder="Enter the address"
                                            onChange={this.handleInputChange}/>
                                        <div className="invalid-feedback">{addressError}</div>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label className="col-sm-2 col-form-label">ZipCode:</label>
                                    <div className="col-sm-8">
                                        <input
                                            type="text"
                                            className={postIndexError ? "form-control is-invalid" : "form-control"}
                                            name="postIndex"
                                            value={postIndex}
                                            placeholder="Enter the index"
                                            onChange={this.handleInputChange}/>
                                        <div className="invalid-feedback">{postIndexError}</div>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label className="col-sm-2 col-form-label">Mobile:</label>
                                    <div className="col-sm-8">
                                        <input
                                            type="text"
                                            className={phoneNumberError ? "form-control is-invalid" : "form-control"}
                                            name="phoneNumber"
                                            value={phoneNumber}
                                            placeholder="(___)-___-____"
                                            onChange={this.handleInputChange}/>
                                        <div className="invalid-feedback">{phoneNumberError}</div>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label className="col-sm-2 col-form-label">Email:</label>
                                    <div className="col-sm-8">
                                        <input
                                            type="text"
                                            className={emailError || validateEmailError ? "form-control is-invalid" : "form-control"}
                                            name="email"
                                            value={email}
                                            placeholder="example@gmail.com"
                                            onChange={this.handleInputChange}/>
                                        <div className="invalid-feedback">{emailError || validateEmailError}</div>
                                    </div>
                                </div>
                            </div>
                            <div className="col-lg-6">
                                <div className="container-fluid">
                                    <div className="row">
                                        {products.map((product) => {
                                            return (
                                                <div key={product.id} className="col-lg-6 d-flex align-items-stretch">
                                                    <div className="card mb-5">
                                             <img src={IMG_URL + product.filename}
                                                 className="rounded mx-auto w-50"/>
                                                        <div className="card-body text-center">
                                                            <h5>{product.productTitle}</h5>
                                                            <h6>{product.manufacturer}</h6>
                                                            <h6><span>$ {product.price}</span>.00</h6>
                                                        </div>
                                                    </div>
                                                </div>
                                            )
                                        })}
                                    </div>
                                </div>
                                <button type="submit" className="btn btn-primary btn-lg btn-success px-5 float-right">
                                    <FontAwesomeIcon icon={faCheckCircle}/> Pay
                                </button>
                                <div className="row">
                                    <h4>To pay : $ <span>{totalPrice}</span>.00</h4>
                                </div>
                            </div>
                        </div>
                    </form>
                )}
                {paymentStep && !paymentSuccess && (
                    <div className="text-center mt-5">
                        <h3>Processing payment...</h3>
                        <button className="btn btn-success btn-lg mt-3" onClick={this.handlePay}>Simulate Payment</button>
                    </div>
                )}
                {paymentSuccess && (
                    <div className="text-center mt-5">
                        <h3>Payment successful!</h3>
                        <button className="btn btn-primary btn-lg mt-3" onClick={this.handleOrderConfirm}>Confirm Order</button>
                    </div>
                )}
            </div>
        );
    }
}

Order.propTypes = {
    fetchOrder: PropTypes.func.isRequired,
    addOrder: PropTypes.func.isRequired,
    products: PropTypes.array.isRequired,
    errors: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
    products: state.order.products,
    errors: state.order.errors
});

export default connect(mapStateToProps, {fetchOrder, addOrder})(Order);
