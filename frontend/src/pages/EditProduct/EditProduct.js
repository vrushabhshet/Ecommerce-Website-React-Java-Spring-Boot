// EditProduct.js - migrated and refactored from EditPerfume.js
import React, {Component} from 'react';
import PropTypes from "prop-types";
import {connect} from "react-redux";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faEdit} from "@fortawesome/free-solid-svg-icons";

import AccountNavbar from "../../component/AccountNavbar/AccountNavbar";
import {IMG_URL} from "../../utils/constants/url";
import {updateProduct, formReset} from "../../actions/admin-actions";
import {fetchProduct} from "../../actions/product-actions";

class EditProduct extends Component {
    state = {
        id: "",
        productTitle: "",
        manufacturer: "",
        productCategory: "",
        description: "",
        price: "",
        filename: "",
        file: null
    };

    componentDidMount() {
        this.props.fetchProduct(this.props.match.params.id);
        this.props.formReset();
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.setState({
            ...nextProps.product
        });
    }

    onFormSubmit = (event) => {
        event.preventDefault();
        const {
            id, productTitle, manufacturer, productCategory, description, price, file
        } = this.state;
        const bodyFormData = new FormData();
        bodyFormData.append("file", file);
        bodyFormData.append("id", id);
        bodyFormData.append("productTitle", productTitle);
        bodyFormData.append("manufacturer", manufacturer);
        bodyFormData.append("year", 2025); 
        bodyFormData.append("productCategory", productCategory);
        bodyFormData.append("description", description);
        bodyFormData.append("price", price);
        this.props.updateProduct(bodyFormData, this.props.history);
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
            productTitle, manufacturer, productCategory, description, price, filename
        } = this.state;
        const {
            productTitleError, manufacturerError,
            productCategoryError, descriptionError, priceError
        } = this.props.errors;
        return (
            <div>
                <AccountNavbar/>
                <div className="container mt-5">
                    <h4><FontAwesomeIcon className="mr-2" icon={faEdit}/>Edit product</h4>
                    <form onSubmit={this.onFormSubmit}>
                        <div className="col-md-5 mb-5 mt-5">
                            <img src={IMG_URL + `${filename}`}
                                 className="rounded mx-auto w-100 mb-2" alt="product"/>
                            <input type="file" name="file" onChange={this.handleFileChange}/>
                        </div>
                        <div className="form-group row">
                            <label className="col-sm-2 col-form-label">Product title: </label>
                            <div className="col-sm-6">
                                <input
                                    type="text"
                                    className={productTitleError ? "form-control is-invalid" : "form-control"}
                                    name="productTitle"
                                    value={productTitle}
                                    onChange={this.handleInputChange}/>
                                <div className="invalid-feedback">{productTitleError}</div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label className="col-sm-2 col-form-label">Manufacturer: </label>
                            <div className="col-sm-6">
                                <input
                                    type="text"
                                    className={manufacturerError ? "form-control is-invalid" : "form-control"}
                                    name="manufacturer"
                                    value={manufacturer}
                                    onChange={this.handleInputChange}/>
                                <div className="invalid-feedback">{manufacturerError}</div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label className="col-sm-2 col-form-label">Category: </label>
                            <div className="col-sm-6">
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
                        <div className="form-group row">
                            <label className="col-sm-2 col-form-label">Description: </label>
                            <div className="col-sm-6">
                                <textarea
                                    className={descriptionError ? "form-control is-invalid" : "form-control"}
                                    name="description"
                                    value={description}
                                    onChange={this.handleInputChange}/>
                                <div className="invalid-feedback">{descriptionError}</div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label className="col-sm-2 col-form-label">Price: </label>
                            <div className="col-sm-6">
                                <input
                                    type="text"
                                    className={priceError ? "form-control is-invalid" : "form-control"}
                                    name="price"
                                    value={price}
                                    onChange={this.handleInputChange}/>
                                <div className="invalid-feedback">{priceError}</div>
                            </div>
                        </div>
                        <button type="submit" className="btn btn-dark">
                            <FontAwesomeIcon className="mr-2" icon={faEdit}/>Edit
                        </button>
                    </form>
                </div>
            </div>
        );
    }
}

EditProduct.propTypes = {
    updateProduct: PropTypes.func.isRequired,
    fetchProduct: PropTypes.func.isRequired,
    formReset: PropTypes.func.isRequired,
    admin: PropTypes.object.isRequired,
    product: PropTypes.object.isRequired
};

const mapStateToProps = (state) => ({
    errors: state.admin.errors,
    product: state.product.product
});

const mapDispatchToProps = (dispatch) => {
    return {
        updateProduct: (data, history) => dispatch(updateProduct(data, history)),
        fetchProduct: (id) => dispatch(fetchProduct(id)),
        formReset: () => dispatch(formReset())
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(EditProduct);
