import React, { Component } from 'react';
import Carousel from "react-bootstrap/Carousel";
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import PropTypes from "prop-types";

import { IMG_URL } from "../../utils/constants/url";
import { fetchProducts } from "../../actions/product-actions";

class ProductCardsSlider extends Component {
    componentDidMount() {
        this.props.fetchProducts();
    }

    getRandomProducts = (products) => {
        if (!products || products.length === 0) return [];
        const shuffled = [...products].sort(() => 0.5 - Math.random());
        return shuffled.slice(0, 3); 
    }

    render() {
        const { products } = this.props;
        const settings = { controls: false };

        const randomProducts = this.getRandomProducts(products);

        return (
            <div>
                <div className="container text-center my-3">
                    <h3>PERSONALLY RECOMMENDED</h3>
                </div>
                <div className="container mt-5" id="indicators">
                    <Carousel {...settings}>
                        {randomProducts.map((product) => (
                            <Carousel.Item key={product.id}>
                                <div className="d-flex justify-content-center">
                                    <div className="card text-center" style={{ width: '18rem', maxHeight: '350px' }}>
                                        <img className="card-img-top mx-auto my-3"
                                            src={IMG_URL + product.filename}
                                            alt={product.productTitle}
                                            style={{ maxHeight: '200px', objectFit: 'contain' }}
                                        />
                                        <div className="card-body">
                                            <h5 className="card-title">{product.productTitle}</h5>
                                            <h6 className="card-subtitle mb-2 text-muted">{product.manufacturer}</h6>
                                            <h6>$<span>{product.price}</span>.00</h6>
                                            <Link to={`/product/${product.id}`} className="btn btn-dark mt-2">
                                                SHOW MORE
                                            </Link>
                                        </div>
                                    </div>
                                </div>
                            </Carousel.Item>
                        ))}
                    </Carousel>
                </div>
            </div>
        );
    }
}

ProductCardsSlider.propTypes = {
    fetchProducts: PropTypes.func.isRequired,
    products: PropTypes.array.isRequired
};

const mapStateToProps = (state) => ({
    products: state.product.products,
});

export default connect(mapStateToProps, { fetchProducts })(ProductCardsSlider);










// import React, {Component} from 'react';
// import Carousel from "react-bootstrap/Carousel";
// import {Link} from "react-router-dom";
// import {connect} from "react-redux";
// import PropTypes from "prop-types";

// import {IMG_URL} from "../../utils/constants/url";
// import {fetchProducts} from "../../actions/product-actions";


// class ProductCardsSlider extends Component {
//     componentDidMount() {
//         this.props.fetchProducts();
//     }

//     addCarouselItems = (array, counter) => {
//         const productsId = [39, 56, 119, 59, 47, 95, 89, 98, 52, 40, 92, 99];

       
//         return (
//             <Carousel.Item>
//                 <div className="card-deck">
//                     {array.map((product) => {
//                         for (let i = counter; i < counter + 4; i++) {
//                             if (product.id === productsId[i]) {
//                                 return (
//                                     <div className="card" key={product.id}>
//                                         <img className="d-block mx-auto w-50"
//                                              src={IMG_URL + `${product.filename}`}/>
//                                         <div className="card-body text-center">
//                                             <h5>{product.productTitle}</h5>
//                                             <h6>{product.manufacturer}</h6>
//                                             <h6>$<span>{product.price}</span>.00</h6>
//                                             <Link to={`/product/${product.id}`}>
//                                             <span className="btn btn-dark">
//                                                 SHOW MORE
//                                             </span>
//                                             </Link>
//                                         </div>
//                                     </div>
//                                 )
//                             }
//                         }
//                     })}
//                 </div>
//             </Carousel.Item>
//         );
//     }

//     render() {
//         const {products} = this.props;
//         const settings = {controls: false}
//         return (
//             <div>
//                 <div className="container text-center my-3">
//                     <h3>PERSONALLY RECOMMENDED</h3>
//                 </div>
//                 <div className="container mt-5" id="indicators">
//                     <form method="get" action="/">
//                         <Carousel {...settings}>
//                             {this.addCarouselItems(products, 0)}
//                             {this.addCarouselItems(products, 4)}
//                             {this.addCarouselItems(products, 8)}
//                         </Carousel>
//                     </form>
//                 </div>
//             </div>
//         );
//     }
// }

// ProductCardsSlider.propTypes = {
//     fetchProducts: PropTypes.func.isRequired,
//     products: PropTypes.array.isRequired
// };

// const mapStateToProps = (state) => ({
//     products: state.product.products,
// });

// export default connect(mapStateToProps, {fetchProducts})(ProductCardsSlider);
