import React from 'react';
import {Link} from "react-router-dom";

const HomePageTheme = () => {
    return (
        <div className="container mt-5">
            <div className="row">
                <div className="col-lg-6">
                    <div className="card mb-5" >
                        <Link to={{pathname: "/menu", state: { id: "food" }}}>
                            <img className="img-fluid" src="images/FOOD.png"/>
                        </Link>
                    </div>
                </div>
                <div className="col-lg-6">
                    <div className="card mb-5">
                        <Link to={{pathname: "/menu", state: { id: "furniture" }}}>
                            <img className="img-fluid" src="/images/Furniture.png"/>
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default HomePageTheme;