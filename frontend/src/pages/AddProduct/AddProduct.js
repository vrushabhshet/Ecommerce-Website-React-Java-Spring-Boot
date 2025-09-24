import React, {Component} from 'react';
import PropTypes from "prop-types";
import {connect} from "react-redux";
import {faPlusSquare} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

import ToastShow from "../../component/Toasts/ToastShow";
import AccountNavbar from "../../component/AccountNavbar/AccountNavbar";
import {addProduct, formReset} from "../../actions/admin-actions";

class AddProduct extends Component {
    initialState = {
        productTitle: "",
        manufacturer: "",
        productCategory: "",
        description: "",
        price: "",
        file: null
    };

    state = {
        ...this.initialState,
        showToast: false
    };

    componentDidMount() {
        this.props.formReset();
    }

    onFormSubmit = (event) => {
        event.preventDefault();



        const {
            productTitle, manufacturer, productCategory, description, price, file
        } = this.state;

        const bodyFormData = new FormData();

        bodyFormData.append("file", file);
        bodyFormData.append("productTitle", productTitle);
        bodyFormData.append("manufacturer", manufacturer);
        bodyFormData.append("year", 2025); // Default year value
        bodyFormData.append("productCategory", productCategory);
        bodyFormData.append("description", description);
        bodyFormData.append("price", price);

        this.props.addProduct(bodyFormData)
            .then(() => {
                if (this.props.success) {
                    this.setState({
                        ...this.initialState,
                        showToast: true
                    });
                    setTimeout(() => this.setState({showToast: false}), 5000);
                    window.scrollTo(0, 0);
                }
            });
    };

    handleFileChange = (event) => {
        this.setState({
            file: event.target.files[0]
        });
    };

    handleInputChange = (event) => {
        const {name, value} = event.target;

        this.setState({
            [name]: value
        });
    };

    render() {
        const {
            productTitle, manufacturer, productCategory, description, price, showToast
        } = this.state;

        const {
            productTitleError, manufacturerError,
            productCategoryError, descriptionError, priceError
        } = this.props.errors;

        return (
            <div>
                <AccountNavbar/>
                <div className="container" style={{"display": showToast ? "block" : "none"}}>
                    <ToastShow showToast={showToast} message={"Product successfully added!"}/>
                </div>
                <div className="container mt-5">
                    <h4><FontAwesomeIcon className="mr-2" icon={faPlusSquare}/>Add product</h4>
                    <br/>
                    <form onSubmit={this.onFormSubmit}>
                        <div className="form row">
                            <div className="col">
                                <label>Product title: </label>
                                <input
                                    type="text"
                                    className={productTitleError ? "form-control is-invalid" : "form-control"}
                                    name="productTitle"
                                    value={productTitle}
                                    placeholder="Enter the product title"
                                    onChange={this.handleInputChange}/>
                                <div className="invalid-feedback">{productTitleError}</div>
                            </div>
                            <div className="col">
                                <label>Manufacturer: </label>
                                <input
                                    type="text"
                                    className={manufacturerError ? "form-control is-invalid" : "form-control"}
                                    name="manufacturer"
                                    value={manufacturer}
                                    placeholder="Enter the manufacturer"
                                    onChange={this.handleInputChange}/>
                                <div className="invalid-feedback">{manufacturerError}</div>
                            </div>
                        </div>
                        <div className="form row mt-3">
                            <div className="col">
                                <label>Category: </label>
                                <select
                                    className={productCategoryError ? "form-control is-invalid" : "form-control"}
                                    name="productCategory"
                                    value={productCategory}
                                    onChange={this.handleInputChange}>
                                    <option value="">Select category</option>
                                    <option value="food">Food</option>
                                    <option value="furniture">Furniture</option>
                                </select>
                                <div className="invalid-feedback">{productCategoryError}</div>
                            </div>
                        </div>
                        <div className="form row mt-3">
                            <div className="col">
                                <label>Price: </label>
                                <input
                                    type="text"
                                    className={priceError ? "form-control is-invalid" : "form-control"}
                                    name="price"
                                    value={price}
                                    placeholder="Enter the price"
                                    onChange={this.handleInputChange}/>
                                <div className="invalid-feedback">{priceError}</div>
                            </div>
                            <div className="col" style={{marginTop: "35px"}}>
                                <input type="file"
                                       name="file"
                                       onChange={this.handleFileChange}/>
                            </div>
                        </div>
                        <button type="submit" className="btn btn-dark mt-3">
                            <FontAwesomeIcon className="mr-2" icon={faPlusSquare}/>Add
                        </button>
                    </form>
                </div>
            </div>
        );
    }
}

AddProduct.propTypes = {
    addProduct: PropTypes.func.isRequired,
    formReset: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired,
    success: PropTypes.bool.isRequired
};

const mapStateToProps = (state) => ({
    errors: state.admin.errors,
    success: state.admin.success,
});

export default connect(mapStateToProps, {addProduct, formReset})(AddProduct);
