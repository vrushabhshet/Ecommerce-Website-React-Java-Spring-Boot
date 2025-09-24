import React from 'react';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faList} from "@fortawesome/free-solid-svg-icons";

import usePagination from "../../component/Pagination/usePagination";
import AccountNavbar from "../../component/AccountNavbar/AccountNavbar";
import PaginationItem from "../../component/Pagination/PaginationItem";
import SearchForm from "../../component/SearchForm/SearchForm";
import ProductCardItem from "../../component/ProductCardItem/ProductCardItem";

function EditProducts({data, itemsPerPage, startFrom, searchByData}) {
    const {slicedData, pagination, prevPage, nextPage, changePage, setFilteredData, setSearching} = usePagination({
        itemsPerPage,
        data,
        startFrom
    });

    return (
        <div>
            <AccountNavbar/>
            <div className="container mt-5">
                <h4><FontAwesomeIcon className="ml-2 mr-2" icon={faList}/> List of products</h4>
                <br/>
                <div className="container form row">
                    <PaginationItem
                        pagination={pagination}
                        prevPage={prevPage}
                        changePage={changePage}
                        nextPage={nextPage}/>
                    <div className="ml-5">
                        <SearchForm
                            data={data}
                            searchByData={searchByData}
                            setFilteredData={setFilteredData}
                            setSearching={setSearching}/>
                    </div>
                </div>
                <div className="container-fluid mt-3">
                    <div className="row">
                        {slicedData.map((product) => {
                            return (
                                <ProductCardItem
                                    product={product}
                                    colSize={2}
                                    link={"/product/list/edit"}
                                    btnName={"Edit"}/>
                            );
                        })}
                    </div>
                </div>
                <PaginationItem
                    pagination={pagination}
                    prevPage={prevPage}
                    changePage={changePage}
                    nextPage={nextPage}/>
            </div>
        </div>
    );
}

export default EditProducts;