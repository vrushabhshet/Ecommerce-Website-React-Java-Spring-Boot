import {combineReducers} from "redux";

import productReducer from "../reducers/product-reducer";
import authReducer from "../reducers/auth-reducer";
import cartReducer from "../reducers/cart-reducer";
import adminReducer from "../reducers/admin-reducer";
import orderReducer from "../reducers/order-reducer";
import userReducer from "../reducers/user-reducer";

const rootReducer = combineReducers({
    product: productReducer,
    auth: authReducer,
    cart: cartReducer,
    admin: adminReducer,
    order: orderReducer,
    user: userReducer
});

export default rootReducer;