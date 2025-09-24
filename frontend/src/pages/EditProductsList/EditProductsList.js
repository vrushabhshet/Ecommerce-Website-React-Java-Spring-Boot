import React, {Component} from 'react';
import PropTypes from "prop-types";
import {connect} from "react-redux";

import EditProducts from "./EditProducts";
import {fetchProducts} from "../../actions/product-actions";

class EditProductsList extends Component {

    componentDidMount() {
    this.props.fetchProducts();
    }

    render() {
    const {products} = this.props;
        const itemsPerPage = 24;
        const searchByData = [
            {label: 'Manufacturer', value: 'manufacturer'},
            {label: 'Product title', value: 'productTitle'},
            {label: 'Category', value: 'productCategory'}
        ];

        return (
            <EditProducts
                data={products}
                itemsPerPage={itemsPerPage}
                searchByData={searchByData}/>
        );
    }
}

EditProductsList.propTypes = {
    fetchProducts: PropTypes.func.isRequired,
    products: PropTypes.array.isRequired
};

const mapStateToProps = (state) => ({
    products: state.product.products
});

export default connect(mapStateToProps, {fetchProducts})(EditProductsList);
