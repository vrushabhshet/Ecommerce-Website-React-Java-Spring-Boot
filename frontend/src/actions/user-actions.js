import axios from 'axios';

import {
    USER_UPDATED_SUCCESS,
    USER_ADDED_REVIEW_SUCCESS,
    USER_ADDED_REVIEW_FAILURE
} from "../utils/constants/actions-types";
import {API_BASE_URL} from "../utils/constants/url";

export const updateUserInfo = (userData, history) => async (dispatch) => {
    const email = localStorage.getItem("email");
    await axios({
        method: "PUT",
        url: API_BASE_URL + "/user/edit?email=" + email,
        data: userData,
        headers: {
            "Content-Type" : "application/json"
        }
    });

    dispatch({
        type: USER_UPDATED_SUCCESS
    });
};

export const addReviewToProduct = (data, productId) => async (dispatch) => {
    const email = localStorage.getItem("email");
    if (!email) {
        window.location.href = "/login";
        return;
    }
    try {
        const payload = {
            author: data.get ? data.get("author") : data.author,
            message: data.get ? data.get("message") : data.message
        };
        await axios({
            method: "POST",
            url: API_BASE_URL + "/user/review?email=" + email + "&ProductId=" + productId,
            data: payload,
            headers: {
                "Content-Type": "application/json"
            }
        });

        dispatch({
            type: USER_ADDED_REVIEW_SUCCESS
        });
        window.location.reload();
    } catch (error) {
        dispatch({
            type: USER_ADDED_REVIEW_FAILURE,
            payload: error.response && error.response.data ? error.response.data : { error: 'Unknown error' }
        });
    }
};
