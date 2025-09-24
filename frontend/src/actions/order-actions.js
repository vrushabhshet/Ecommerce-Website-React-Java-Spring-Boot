import axios from 'axios';

import {
    FETCH_ORDER_SUCCESS,
    ORDER_ADDED_SUCCESS,
    ORDER_ADDED_FAILURE,
    ORDER_CONFIRMED_SUCCESS,
    FETCH_USER_ORDERS_SUCCESS
} from "../utils/constants/actions-types";
import {API_BASE_URL} from "../utils/constants/url";

export const fetchOrder = () => async (dispatch) => {
    const email = localStorage.getItem("email");
    const response = await axios({
        method: "GET",
        url: API_BASE_URL + "/order?email=" + email,
        headers: {
            "Content-Type": "application/json"
        }
    });

    dispatch({
        type: FETCH_ORDER_SUCCESS,
        payload: response.data
    })
};

export const addOrder = (order, history) => async (dispatch) => {
    try {
        const email = localStorage.getItem("email");
        const response = await axios({
            method: "POST",
            url: API_BASE_URL + "/order?email=" + email,
            data: order,
            headers: {
                "Content-Type": "application/json"
            }
        });

        history.push("/order/finalize");

        dispatch({
            type: ORDER_ADDED_SUCCESS,
            payload: response.data
        })
    } catch (error) {
        dispatch({
            type: ORDER_ADDED_FAILURE,
            payload: error.response.data
        })
    }
};

export const fetchUserOrders = () => async (dispatch) => {
    const email = localStorage.getItem("email");
    const response = await axios({
        method: "GET",
        url: API_BASE_URL + "/user/orders?email=" + email,
        headers: {
            "Content-Type": "application/json"
        }
    });

    dispatch({
        type: FETCH_USER_ORDERS_SUCCESS,
        payload: response.data
    })
};

export const finalizeOrder = () => async (dispatch) => {
    const email = localStorage.getItem("email");
    const response = await axios.get(API_BASE_URL + "/order/finalize?email=" + email);

    dispatch({
        type: ORDER_CONFIRMED_SUCCESS,
        payload: response.data
    })
};
