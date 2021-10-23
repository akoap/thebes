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

    function reset() {
        setName("");
        setDescription("");
        setCouponType("");
        setExpiration("");
    }

    function handleSubmit(e) {
        console.log(name, description, couponType, expiration)

        reset();
    }

    return (
        <Fragment>
            <div className="analytics m-sm-30 mt-6">
                <Grid container spacing={3}>
                    {/* This Grid is for businesses to add coupons */}
                    <Grid item lg={12} md={12} sm={12} xs={12}>
                        <TextField id="filled-basic" label="name" variant="filled" value={name} onChange={(e) => setName(e.target.value)} />
                        <TextField id="filled-basic" label="description" variant="filled" value={description} onChange={(e) => setDescription(e.target.value)} />
                        <TextField id="filled-basic" label="couponType" variant="filled" value={couponType} onChange={(e) => setCouponType(e.target.value)} />
                        <TextField id="filled-basic" label="expiration" variant="filled" value={expiration} onChange={(e) => setExpiration(e.target.value)} />

                        <Button onClick={handleSubmit} variant="contained">Contained</Button>
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
