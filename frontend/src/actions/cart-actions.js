import axios from 'axios';

import {
    FETCH_CART_SUCCESS,
    PRODUCT_ADDED_TO_CART_SUCCESS,
    PRODUCT_REMOVED_FROM_CART_SUCCESS,
    LOADING_CART
} from "../utils/constants/actions-types";
import {API_BASE_URL} from "../utils/constants/url";

export const fetchCart = () => async (dispatch) => {
    dispatch({
        type: LOADING_CART
    });

    const email = localStorage.getItem("email");
    if (!email) {
        dispatch({
            type: FETCH_CART_SUCCESS,
            payload: []
        });
        return;
    }

    const response = await axios({
        method: "GET",
        url: API_BASE_URL + "/cart/" + email,
        headers: {
            "Content-Type": "application/json"
        }
    });

    dispatch({
        type: FETCH_CART_SUCCESS,
        payload: response.data
    });
};

export const addToCart = (product, history) => async (dispatch) => {
    const email = localStorage.getItem("email");
    await axios({
        method: "POST",
        url: API_BASE_URL + "/cart/add?email=" + email,
    data: product,
        headers: {
            "Content-Type": "application/json"
        }
    });

    dispatch({
        type: PRODUCT_ADDED_TO_CART_SUCCESS,
    });

    history.push("/cart");
};

export const removeFromCart = (product) => async (dispatch) => {
    const email = localStorage.getItem("email");
    const response = await axios({
        method: "POST",
        url: API_BASE_URL + "/cart/remove?email=" + email,
        data: product,
        headers: {
            "Content-Type": "application/json"
        }
    });

    dispatch({
        type: PRODUCT_REMOVED_FROM_CART_SUCCESS,
        payload: response.data,
    });
};
