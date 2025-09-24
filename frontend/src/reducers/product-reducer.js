import {
    FETCH_PRODUCTS,
    FETCH_PRODUCT,
    FETCH_PRODUCTS_BY_CATEGORY,
    FETCH_PRODUCTS_BY_MANUFACTURER,
    FETCH_PRODUCTS_BY_FILTER_PARAMS
} from "../utils/constants/actions-types";

const initialState = {
    products: [],
    product: {},
    reviews: []
};

const reducer = (state = initialState, action) => {
    const {type, payload} = action

    switch (type) {
        case FETCH_PRODUCTS:
            return {...state, products: payload};

        case FETCH_PRODUCT:
            return {...state, product: payload, reviews: payload.reviews};

        case FETCH_PRODUCTS_BY_CATEGORY:
            return {...state, products: payload};

        case FETCH_PRODUCTS_BY_MANUFACTURER:
            return {...state, products: payload};

        case FETCH_PRODUCTS_BY_FILTER_PARAMS:
            return {...state, products: payload};

        default:
            return state;
    }
};

export default reducer;