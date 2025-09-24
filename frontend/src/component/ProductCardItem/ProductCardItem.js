import React, {useState} from 'react';
import {Link} from "react-router-dom";

import {IMG_URL} from "../../utils/constants/url";
import Spinner from "../Spinner/Spinner";

const ProductCardItem = ({product, colSize, link, btnName}) => {
    const [load, setLoad] = useState(false);

    return (
        <div className={`col-lg-${colSize} d-flex align-items-stretch`}>
            <div className="card mb-5">
                {load ? null :
                    <div className="d-block mx-auto w-50">
                        <Spinner/>
                    </div>
                }
                <img onLoad={() => setLoad(true)}
                    className="mx-auto w-50"
                    style={{display: load ? "block" : "none"}}
                    src={IMG_URL + product.filename}
                    alt={product.productTitle}
                />

                <div className="card-body text-center">
                    <h5>{product.productTitle}</h5>
                    <h6>{product.manufacturer}</h6>
                    <h6><span>${product.price}</span>.00</h6>
                </div>
                <div className="text-center align-items-end mb-3">
                    <Link to={`${link}/${product.id}`}>
                        <span className="btn btn-dark">{btnName}</span>
                    </Link>
                </div>
            </div>
        </div>
    )
}

export default ProductCardItem;