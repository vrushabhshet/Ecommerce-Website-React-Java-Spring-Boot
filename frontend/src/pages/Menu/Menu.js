import { manufacturer, category, price } from "./MenuData";
import React, {Component} from "react";
import {Route, withRouter} from "react-router-dom";
import {connect} from "react-redux";
import PropTypes from "prop-types";

import Checkbox from "../../component/CheckBox/Checkbox";
import CheckboxRadio from "../../component/CheckboxRadio/CheckboxRadio";
import MenuCards from "../../component/MenuCards/MenuCards";
import {
    fetchProducts,
    fetchProductsByManufacturer,
    fetchProductsByCategory,
    fetchProductsByFilterParams
} from "../../actions/product-actions";
import "./MenuStyle.css";

class Menu extends Component {
    state = {
        filterParams: {
            manufacturers: [],
            categories: [],
            prices: []
        }
    };

    componentDidMount() {
        // Try to get category from query string
        const params = new URLSearchParams(this.props.location.search);
        const categoryParam = params.get('category');
        if (categoryParam === "food" || categoryParam === "furniture") {
            // Set categories filter in state and call getProducts
            const newFilters = { ...this.state.filterParams, categories: [categoryParam] };
            this.setState({ filterParams: newFilters }, () => {
                this.getProducts(newFilters);
            });
            window.scrollTo(0, 0);
        } else if (categoryParam === "all") {
            this.props.fetchProducts();
            window.scrollTo(0, 0);
        } else if (this.props.location.state && this.props.location.state.id) {
            // fallback for old state-based navigation
            const productData = this.props.location.state.id;
            if (productData === "food" || productData === "furniture") {
                const newFilters = { ...this.state.filterParams, categories: [productData] };
                this.setState({ filterParams: newFilters }, () => {
                    this.getProducts(newFilters);
                });
                window.scrollTo(0, 0);
            } else if (productData === "all") {
                this.props.fetchProducts();
                window.scrollTo(0, 0);
            } else if (productData) {
                this.props.fetchProductsByManufacturer({manufacturer: productData});
                window.scrollTo(0, 0);
            }
        }
    }

    getProducts = (variables) => {
        this.props.fetchProductsByFilterParams(variables);
    };

    handlePrice = (value) => {
        const data = price;
        let array = [];

        for (let key in data) {
            if (data[key].id === parseInt(value, 10)) {
                array = data[key].array;
            }
        }

        return array
    };

    handleFilters = (filters, category) => {
        const newFilters = {...this.state.filterParams};
        newFilters[category] = filters;

        if (category === "prices") {
            let priceValues = this.handlePrice(filters);
            newFilters[category] = priceValues;
        }

        this.getProducts(newFilters);
        this.setState({filterParams: newFilters});
    };

    render() {
    const {products} = this.props;

        return (
            <div className="container d-flex">
                <nav id="sidebar">
                    <div className="sidebar-header">
                        <h3>Products</h3>
                    </div>
                    <ul className="list-unstyled components">
                        <h5>Manufacturer</h5>
                        <li className="active mb-2" id="homeSubmenu">
                            <Checkbox list={manufacturer}
                                      handleFilters={(filters) => this.handleFilters(filters, "manufacturers")}/>
                        </li>
                        <h5>Category</h5>
                        <li className="active mb-2">
                            <Checkbox
                                list={category}
                                handleFilters={(filters) => this.handleFilters(filters, "categories")}
                                checkedItems={this.state.filterParams.categories}
                            />
                        </li>
                        <h5>Price</h5>
                        <li className="active mb-2">
                            <CheckboxRadio list={price}
                                           handleFilters={(filters) => this.handleFilters(filters, "prices")}/>
                        </li>
                    </ul>
                </nav>
                <Route exact component={() => <MenuCards data={products} itemsPerPage={16} searchByData={[
                    {label: 'Manufacturer', value: 'manufacturer'},
                    {label: 'Product title', value: 'productTitle'}]}/>}/>
            </div>
        );
    }
}

Menu.propTypes = {
    fetchProducts: PropTypes.func.isRequired,
    fetchProductsByManufacturer: PropTypes.func.isRequired,
    fetchProductsByCategory: PropTypes.func.isRequired,
    fetchProductsByFilterParams: PropTypes.func.isRequired,
    products: PropTypes.array.isRequired
};

const mapStateToProps = (state) => ({
    products: state.product.products,
});

export default withRouter(connect(mapStateToProps, {
    fetchProducts,
    fetchProductsByManufacturer,
    fetchProductsByCategory,
    fetchProductsByFilterParams
})(Menu));
