
import axios from 'axios';
import {
    PRODUCT_ADDED_SUCCESS,
    PRODUCT_UPDATED_SUCCESS,
    PRODUCT_ADDED_FAILURE,
    PRODUCT_UPDATED_FAILURE,
    FETCH_USER_SUCCESS,
    FETCH_ALL_USERS_SUCCESS,
    FETCH_ALL_USERS_ORDERS_SUCCESS,
    FORM_RESET
} from "../utils/constants/actions-types";
import {API_BASE_URL} from "../utils/constants/url";

export const updateUser = (userData) => async (dispatch) => {
    try {
        const formData = new FormData();
    formData.append("username", userData.username);
    formData.append("userId", userData.id);
    formData.append("role", userData.roles);

        const response = await axios({
            method: "PUT",
            url: API_BASE_URL + "/admin/user/edit",
            data: formData,
            headers: {
                "Content-Type": "multipart/form-data"
            }
        });
        dispatch({
            type: "USER_UPDATE_SUCCESS",
            payload: response.data
        });
    } catch (error) {
        dispatch({
            type: "USER_UPDATE_FAILURE",
            payload: error.response ? error.response.data : error.message
        });
    }
};

export const addProduct = (data) => async (dispatch) => {
    try {
        const response = await axios({
            method: "POST",
            url: API_BASE_URL + "/admin/add",
            data: data,
            headers: {
                "Content-Type": "multipart/form-data"
            }
        });

        dispatch({
            type: PRODUCT_ADDED_SUCCESS,
            payload: response.data
        })
    } catch (error) {
        dispatch({
            type: PRODUCT_ADDED_FAILURE,
            payload: error.response.data
        })
    }
};

export const updateProduct = (data, history) => async (dispatch) => {
    try {
        const response = await axios({
                method: "PUT",
                url: API_BASE_URL + "/admin/edit",
                data: data,
                headers: {
                    "Content-Type": "multipart/form-data"
                }
            });

        history.push("/account");

        dispatch({
            type: PRODUCT_UPDATED_SUCCESS,
            payload: response.data
        })
    } catch (error) {
        dispatch({
            type: PRODUCT_UPDATED_FAILURE,
            payload: error.response.data
        })
    }
};

export const fetchAllUsersOrders = () => async (dispatch) => {
    const response = await axios({
        method: "GET",
        url: API_BASE_URL + "/admin/orders",
        headers: {
            "Content-Type" : "application/json"
        }
    });

    dispatch({
        type: FETCH_ALL_USERS_ORDERS_SUCCESS,
        payload: response.data
    })
};

export const fetchAllUsers = () => async (dispatch) => {
    const response = await axios({
        method: "GET",
        url: API_BASE_URL + "/admin/user/all",
        headers: {
            "Content-Type" : "application/json"
        }
    });

    dispatch({
        type: FETCH_ALL_USERS_SUCCESS,
        payload: response.data
    })
};

export const fetchUser = (id) => async (dispatch) => {
    const response = await axios({
        method: "GET",
        url: API_BASE_URL + "/admin/user/" + id,
        headers: {
            "Content-Type" : "application/json"
        }
    });

    dispatch({
        type: FETCH_USER_SUCCESS,
        payload: response.data
    })
};

export const formReset = () => async (dispatch) => {
    dispatch({
        type: FORM_RESET
    })
};
