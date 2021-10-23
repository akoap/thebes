import React, { Fragment, useState, useEffect } from 'react'
import { Grid, Card, TextField, Button } from '@material-ui/core'
import DoughnutChart from './shared/Doughnut'
import StatCards from './shared/StatCards'
import TopSellingTable from './shared/TopSellingTable'
import RowCards from './shared/RowCards'
import StatCards2 from './shared/StatCards2'
import UpgradeCard from './shared/UpgradeCard'
import Campaigns from './shared/Campaigns'
import { useTheme } from '@material-ui/styles'
import _ from 'lodash'

const Analytics = () => {
    const theme = useTheme()

    // these constantly store the current inputted values
    const [ name, setName ] = useState("");
    const [ description, setDescription ] = useState("");
    const [ couponType, setCouponType ] = useState("");
    const [ expiration, setExpiration ] = useState("");
    const [ chipId, setChipId ] = useState("");

    function reset() {
        setName("");
        setDescription("");
        setCouponType("");
        setExpiration("");
        setChipId("");
    }

    async function handleSubmit(e) {
        console.log(name, description, couponType, expiration, chipId)



        const couponEndpoint = 'https://us-east1-thebes-329917.cloudfunctions.net/thebes-backend?first=' + name + '&second=' + description + '&third=' + expiration;


        fetch(couponEndpoint)
        .then(resonse => resonse.text())
        .then(data => console.log(data));

        



        // const fetch = require("node-fetch");
        

        // let date = new Date(); // initialize our date

        // let options = {
        //     date,
        //     nepOrganization: "test-drive-3611d66f193f424295a44",
        //     requestURL: "https://api.ncr.com/order/3/orders/1/12388465659725447903",
        //     httpMethod: "GET",
        //     contentType: "application/json",
        // };

        // const username = "f7a71501-fa5c-46eb-baac-ed438c4dc443";
        // const password = "Password123456!";

        // console.log(`${username}:${password}`)

        // let requestOptions = {
        //     method: options.httpMethod,
        //     headers: {
        //         "Content-Type": options.contentType,
        //         Authorization: `Basic ${btoa(`${username}:${password}`)}`,
        //         "nep-organization": options.nepOrganization,
        //         Date: date.toGMTString(),
        //     },
        // };

        // const response = await fetch(options.requestURL, requestOptions);
        // const data = await response.json();

        // console.log('RECEIVED DATA');
        // console.log(data);

        reset();
    }

    return (
        <Fragment>
            <div className="analytics m-sm-30 mt-6">
                <Grid container spacing={3}>
                    {/* This Grid is for businesses to add coupons */}
                    <Grid item lg={12} md={12} sm={12} xs={12}>
                        <TextField id="filled-basic" label="Name" variant="filled" value={name} onChange={(e) => setName(e.target.value)} />
                        <TextField id="filled-basic" label="Description" variant="filled" value={description} onChange={(e) => setDescription(e.target.value)} />
                        <TextField id="filled-basic" label="Coupon Type" variant="filled" value={couponType} onChange={(e) => setCouponType(e.target.value)} />
                        <TextField id="filled-basic" label="Expiration" variant="filled" value={expiration} onChange={(e) => setExpiration(e.target.value)} />
                        <TextField id="filled-basic" label="Chip Id" variant="filled" value={chipId} onChange={(e) => setChipId(e.target.value)} />
                        <br />
                        <Button onClick={handleSubmit} variant="contained">Generate Coupon</Button>
                        {/* "name":"$4 Off $40 total purchase",
                        "description":"$4 Off $40 total purchase",
                        "couponType":"Store",
                        "effective":"2014-08-17",
                        "expiration":"2014-09-21T23:59:59", */}
                    </Grid>
                    <Grid item lg={12} md={12} sm={12} xs={12}>
                        {/* <StatCards /> */}

                        {/* Top Selling Products */}
                        <TopSellingTable />

                        {/* <StatCards2 /> */}

                        {/* <h4 className="card-title text-muted mb-4">
                            Ongoing Projects
                        </h4>
                        <RowCards /> */}
                    </Grid>

                    {/* <Grid item lg={4} md={4} sm={12} xs={12}>
                        <Card className="px-6 py-4 mb-6">
                            <div className="card-title">Traffic Sources</div>
                            <div className="card-subtitle">Last 30 days</div>
                            <DoughnutChart
                                height="300px"
                                color={[
                                    theme.palette.primary.dark,
                                    theme.palette.primary.main,
                                    theme.palette.primary.light,
                                ]}
                            />
                        </Card>

                        <UpgradeCard />

                        <Campaigns />
                    </Grid> */}
                </Grid>
            </div>
        </Fragment>
    )
}

export default Analytics
