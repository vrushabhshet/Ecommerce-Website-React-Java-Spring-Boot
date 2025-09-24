import axios from 'axios';

import {
    FETCH_PRODUCTS,
    FETCH_PRODUCT,
    FETCH_PRODUCTS_BY_CATEGORY,
    FETCH_PRODUCTS_BY_MANUFACTURER,
    FETCH_PRODUCTS_BY_FILTER_PARAMS
} from "../utils/constants/actions-types";
import {API_BASE_URL} from "../utils/constants/url";

export const fetchProducts = () => async (dispatch) => {
    const response = await axios.get(API_BASE_URL);

    dispatch({
        type: FETCH_PRODUCTS,
        payload: response.data
    })
};

export const fetchProduct = (id) => async (dispatch) => {
    const response = await axios.get(API_BASE_URL + "/product/" + id);

    dispatch({
        type: FETCH_PRODUCT,
        payload: response.data
    })
};

export const fetchProductsByCategory = (category) => async (dispatch) => {
    const response = await axios.post(API_BASE_URL + "/menu/category", category);

    dispatch({
        type: FETCH_PRODUCTS_BY_CATEGORY,
        payload: response.data
    })
};

export const fetchProductsByManufacturer = (manufacturer) => async (dispatch) => {
    const response = await axios.post(API_BASE_URL + "/menu/manufacturer", manufacturer);

    dispatch({
        type: FETCH_PRODUCTS_BY_MANUFACTURER,
        payload: response.data
    })
};

export const fetchProductsByFilterParams = (filter) => async (dispatch) => {
    const response = await axios.post(API_BASE_URL + "/menu/search", filter);

    dispatch({
        type: FETCH_PRODUCTS_BY_FILTER_PARAMS,
        payload: response.data
    })
};