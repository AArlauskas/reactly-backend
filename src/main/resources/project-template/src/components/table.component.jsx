import React, { useState, useEffect } from "react";

import MaterialTable from "@material-table/core";

const Table = ({ columns, dataURL, pageSize, title, accessor, modifiers }) => {
    const [data, setData] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        setIsLoading(true);
        fetch(dataURL)
            .then((response) => response.json())
            .then((data) => {
                if (accessor) {
                    setData(data[accessor]);
                    console.log(data[accessor]);
                } else {
                    setData(data);
                }
            })
            .catch((error) => {
                console.log(error);
                setData([]);
            })
            .finally(() => setIsLoading(false));
    }, [dataURL, accessor]);

    return (
        <MaterialTable
            style={modifiers}
            columns={columns || []}
            data={data}
            isLoading={isLoading}
            title={title}
            options={{
                actionsColumnIndex: -1,
                pageSize: pageSize,
            }}
        />
    );
};

export default Table;
